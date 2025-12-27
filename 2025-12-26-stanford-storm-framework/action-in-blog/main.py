from dsp import LM, AWSAnthropic, Bedrock
from knowledge_storm import (
  STORMWikiRunnerArguments,
  STORMWikiRunner,
  STORMWikiLMConfigs,
)

from rag.custom_rm import CustomRetrieveModule
from rag.vector_store import VectorStore


def create_retriever():
  return CustomRetrieveModule(vector_store=VectorStore())


def create_llm() -> LM:
  provider = Bedrock(region_name="ap-northeast-1")
  return AWSAnthropic(provider, "apac.anthropic.claude-sonnet-4-20250514-v1:0")


def create_runner():
  lm_configs = STORMWikiLMConfigs()
  asker_model = create_llm()
  lm_configs.set_conv_simulator_lm(asker_model)
  lm_configs.set_question_asker_lm(asker_model)

  generator_model = create_llm()
  lm_configs.set_outline_gen_lm(generator_model)
  lm_configs.set_article_gen_lm(generator_model)
  lm_configs.set_article_polish_lm(generator_model)

  retriever = create_retriever()

  return STORMWikiRunner(
    args=STORMWikiRunnerArguments(output_dir="output"),
    lm_configs=lm_configs,
    rm=retriever,
  )


def main():
  runner = create_runner()
  runner.run(topic="artificial intelligence")


if __name__ == "__main__":
  main()
