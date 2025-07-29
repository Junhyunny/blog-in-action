import bs4
from langchain.text_splitter import CharacterTextSplitter
from langchain_chroma import Chroma
from langchain_community.document_loaders import WebBaseLoader
from langchain_huggingface import HuggingFaceEmbeddings

wiki_url = "https://en.wikipedia.org/wiki/List_of_presidents_of_the_United_States"

loader = WebBaseLoader(
	web_paths = [wiki_url],
	bs_kwargs = dict(parse_only = bs4.SoupStrainer(class_ = ("mw-body-content", "mw-parser-output")))
)
documents = loader.load()

text_splitter = CharacterTextSplitter(chunk_size = 100, chunk_overlap = 0)
split_documents = text_splitter.split_documents(documents)

embedding_model = HuggingFaceEmbeddings(
	model_name = "intfloat/multilingual-e5-base",
	model_kwargs = {
		"device": "cpu"
	}
)

Chroma.from_documents(
	documents = split_documents,
	embedding = embedding_model,
	persist_directory = "./chroma_llama"
)
