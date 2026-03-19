import asyncio
import json
import random
from typing import Any, Dict

import nest_asyncio
from dotenv import load_dotenv
from langchain.agents import create_agent
from langchain_aws import ChatBedrockConverse
from langchain_core.runnables import RunnableConfig
from langchain_core.tools import tool
from rich.console import Console
from rich.markdown import Markdown
from rich.panel import Panel

nest_asyncio.apply()
load_dotenv()

console = Console(soft_wrap=True)

llm = ChatBedrockConverse(
  model="jp.anthropic.claude-sonnet-4-6",
  region_name="ap-northeast-1",
  temperature=0.7,
)


def print_rule(title: str, color: str) -> None:
  console.rule(f"[bold {color}]{title}[/bold {color}]", style=color)


def print_panel(content: str, title: str, color: str) -> None:
  console.print(
    Panel(
      content,
      title=f"[bold {color}]{title}[/bold {color}]",
      border_style=color,
      expand=True,
    )
  )


def get_input(data: dict) -> str:
  input_data = data.get("input", "")
  if not isinstance(input_data, str):
    input_data = json.dumps(input_data, ensure_ascii=False, indent=2)
  return input_data


def get_message(data: Dict[str, Any]) -> str:
  messages = data["output"].get("messages", [])
  if messages:
    content = messages[-1].content
    if isinstance(content, list):
      return "\n".join(c.get("text", "") if isinstance(c, dict) else str(c) for c in content)
    else:
      return str(content)
  return ""


@tool
def random_number() -> int:
  """Returns a random number between 1 and 10."""
  return random.randint(1, 10)


async def main():
  agent = create_agent(model=llm, tools=[random_number])

  final_answer = ""

  prompts = {
    "messages": [
      {
        "role": "user",
        "content": """
숫자가 30이 넘지 않을 때까지 랜덤한 숫자를 받아서 더해줘.
30이 넘어가는 순간 멈추고 최종 합계를 알려줘.
""",
      }
    ]
  }

  config: RunnableConfig = {"recursion_limit": 100}

  async for event in agent.astream_events(prompts, version="v2", config=config):
    kind = event["event"]
    name = event.get("name", "")
    data = event["data"]

    if kind == "on_chat_model_start":
      print_rule("Agent is Thinking", "yellow")
    elif kind == "on_tool_start":
      tool_input = get_input(data)
      print_panel(tool_input, name, "cyan")
    elif kind == "on_tool_end":
      output = str(data.get("output", ""))[:300]
      print_panel(output, "Tool Result", "green")
    elif kind == "on_chain_end" and name == "LangGraph":
      final_answer = get_message(data)

  console.print()
  print_rule("Final Answer", "magenta")
  console.print(Markdown(final_answer))
  print_rule("", "magenta")


asyncio.run(main())
