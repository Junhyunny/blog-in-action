import pickle

import faiss
from sentence_transformers import SentenceTransformer


class VectorStore:
  def __init__(
    self,
    pickle_path: str = "db/article_id_list.pkl",
    index_path: str = "db/faiss_index.bin",
    model_name: str = "sentence-transformers/paraphrase-multilingual-mpnet-base-v2",
  ):
    self.embedding_model = SentenceTransformer(model_name)
    self._load_index(index_path)
    self._load_pickle(pickle_path)

  def _load_index(self, index_path: str):
    try:
      self.index = faiss.read_index(index_path)
    except Exception as e:
      raise Exception("Index not found. Please run indexing first.")

  def _load_pickle(self, pickle_path: str):
    try:
      with open(pickle_path, "rb") as f:
        self.article_id_list = pickle.load(f)
    except Exception as e:
      raise Exception("Pickle not found. Please run indexing first.")

  def search(self, query: str, top_k: int = 10):
    if self.index is None or self.article_id_list is None:
      raise Exception("Index or pickle not loaded properly.")

    query_embedding = self.embedding_model.encode([query], normalize_embeddings=True)
    distances, indices = self.index.search(query_embedding, top_k)

    results = []
    for distance, id_index in zip(distances[0], indices[0]):
      results.append((self.article_id_list[id_index], distance))

    return results
