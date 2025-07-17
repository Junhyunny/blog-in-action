from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import ChatPromptTemplate
from langchain_ollama import ChatOllama

prompt = ChatPromptTemplate.from_template("Explain about {topic}.")
llm = ChatOllama(model = "llama3.2")
parser = StrOutputParser()

# chain = RunnableSequence(
# 	first = prompt,
# 	middle = [llm],
# 	last = parser
# )
chain = prompt | llm | parser
answer = chain.stream({
	"topic": "deep learning"
})

for chunk in answer:
	print(chunk, end = "", flush = True)
