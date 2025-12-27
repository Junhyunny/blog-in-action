import os
import pickle
import sqlite3
from typing import List

import faiss
from sentence_transformers import SentenceTransformer

from faker_articles import fake_articles, Article


def indexing():
  articles = fake_articles()
  create_faiss_index(list([article.content for article in articles]))
  create_pickle(list([article.id for article in articles]))
  create_sqlite_database(articles)


def create_faiss_index(
  texts: List[str],
  model_name: str = "sentence-transformers/paraphrase-multilingual-mpnet-base-v2",
  index_path: str = "db/faiss_index.bin",
):
  os.makedirs(os.path.dirname("db/"), exist_ok=True)
  model = SentenceTransformer(model_name)
  embeddings = model.encode(texts, normalize_embeddings=True, show_progress_bar=True)
  embeddings = embeddings.astype("float32")
  dimension = embeddings.shape[1]
  index = faiss.IndexFlatL2(dimension)
  index.add(embeddings)
  faiss.write_index(index, index_path)


def create_sqlite_database(
  articles: List[Article], sqlite_path: str = "db/articles.db"
):
  os.makedirs(os.path.dirname("db/"), exist_ok=True)
  conn = sqlite3.connect(sqlite_path)
  cursor = conn.cursor()
  cursor.execute(
    "create table if not exists articles (id integer primary key autoincrement, article_id integer, title TEXT, content TEXT, url TEXT)"
  )
  rows = []
  for article in articles:
    rows.append((article.id, article.title, article.content, article.url))
  cursor.executemany(
    "insert into articles (article_id, title, content, url) values (?, ?, ?, ?)", rows
  )
  conn.commit()
  conn.close()


def create_pickle(
  article_id_list: List[int], pickle_path: str = "db/article_id_list.pkl"
):
  os.makedirs(os.path.dirname("db/"), exist_ok=True)
  with open(pickle_path, "wb") as f:
    pickle.dump(article_id_list, f)


if __name__ == "__main__":
  indexing()
