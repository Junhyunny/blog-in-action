from langchain.chains import LLMChain, RetrievalQA, create_retrieval_chain
from langchain.chains.combine_documents import create_stuff_documents_chain
from langchain.prompts import PromptTemplate
from langchain_chroma import Chroma
from langchain_huggingface import HuggingFaceEmbeddings
from langchain_ollama import ChatOllama

llm = ChatOllama(model = "llama3.2")

prompt = PromptTemplate(
	input_variables = ["context", "input"],
	template = """
			Answer refer contexts:
			context: {context}
			question: {input}
			
			Answer with specific information and details.
			Answer with reference links if you know the source.
			Answer in a way that other users don't feel like you're referencing external sources such as RAG.
			Do not answer with unnecessary words such as reference numbers or inline citation.
	"""
)

embedding_model = HuggingFaceEmbeddings(
	model_name = "intfloat/multilingual-e5-base",
	model_kwargs = {
		"device": "cpu"
	}
)

vectorstore = Chroma(
	persist_directory = "./chroma_llama",
	embedding_function = embedding_model
)

retriever = vectorstore.as_retriever(search_kwargs = {
	"k": 3
})

combine_docs_chain = create_stuff_documents_chain(llm, prompt)

rag_chain = create_retrieval_chain(retriever, combine_docs_chain)

chain = rag_chain.pick("answer")

stream = chain.stream({
	"input": "Who is the president of USA now?"
})
for chunk in stream:
	print(f"{chunk}", end = "")
