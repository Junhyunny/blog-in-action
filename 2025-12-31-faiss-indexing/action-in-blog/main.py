import json
from typing import List

import faiss
import numpy as np
from sentence_transformers import SentenceTransformer

from models.article import Article

model = SentenceTransformer(
  "sentence-transformers/paraphrase-multilingual-mpnet-base-v2"
)


def embedding_articles(
  contents: List[str],
) -> tuple[np.ndarray, int]:
  embeddings = model.encode(contents, normalize_embeddings=True, show_progress_bar=True)
  embeddings = embeddings.astype("float32")
  dimension = embeddings.shape[1]
  return embeddings, dimension


def embedding_query(query: str):
  return model.encode([query], normalize_embeddings=True)


def fetch_articles():
  articles_json = json.loads(open("db/articles.json", "r").read())
  return [Article(item) for item in articles_json]


def indexing_without_id_mapping():
  articles = fetch_articles()

  ids = list(article.id for article in articles)
  contents = list([article.content for article in articles])

  embeddings, dimension = embedding_articles(contents)
  index = faiss.IndexFlatL2(dimension)
  index.add(embeddings)

  query_vector = embedding_query("mindset consulting")
  distances, indices = index.search(query_vector, 5)

  for distance, contents_index in zip(distances[0], indices[0]):
    print(f"distance: {distance}, contents_index: {contents_index}")
    print(f"article's id: {ids[contents_index]}")
    print(f"content: {contents[contents_index]}")


def indexing_with_id_mapping():
  articles = fetch_articles()

  ids = np.array(list(article.id for article in articles))
  contents = list([article.content for article in articles])
  dict_db = dict(zip(ids, contents))

  embeddings, dimension = embedding_articles(contents)
  index = faiss.IndexFlatL2(dimension)
  id_map_index = faiss.IndexIDMap(index)
  id_map_index.add_with_ids(embeddings, ids)

  query_vector = embedding_query("mindset consulting")
  distances, indices = id_map_index.search(query_vector, 5)

  for distance, article_id in zip(distances[0], indices[0]):
    print(f"distance: {distance}")
    print(f"article's id: {article_id}")
    print(f"content: {dict_db[article_id]}")


if __name__ == "__main__":
  print("=" * 10 + " indexing without id mapping " + "=" * 10)
  indexing_without_id_mapping()
  print("=" * 10 + " indexing with id mapping " + "=" * 10)
  indexing_with_id_mapping()
