import sqlite3
from typing import Union, List, Optional

import dspy

from indexing.faker_articles import Article
from rag.vector_store import VectorStore


def to_result(articles: List[Article]):
  result = []
  for article in articles:
    item = {
      "title": article[0],
      "description": article[1],
      "snippets": [article[1]],
      "url": article[2],
    }
    print(f"fetched article: {item}")
    result.append(item)
  return result


class CustomRetrieveModule(dspy.Retrieve):
  def __init__(
    self,
    vector_store: VectorStore,
    sqlite_article_db_path: str = "db/articles.db",
    default_k: int = 5,
  ):
    super().__init__()
    self.vector_store = vector_store
    self.sqlite_article_db_path = sqlite_article_db_path
    self.k = default_k

  def fetch_articles_by_article_id(self, article_id_list: List[int]):
    conn = sqlite3.connect(self.sqlite_article_db_path)
    cur = conn.cursor()
    placeholders = ", ".join(["?"] * len(article_id_list))
    query = f"SELECT title, content, url FROM articles WHERE id IN ({placeholders})"
    cur.execute(query, article_id_list)
    return cur.fetchall()

  def forward(
    self, query_or_queries: Union[str, List[str]], k: Optional[int] = None, **kwargs
  ):
    try:
      queries = (
        query_or_queries if isinstance(query_or_queries, list) else [query_or_queries]
      )
      result = []
      for query in queries:
        print(f"retrieving query: {query}")
        search_results = self.vector_store.search(query, top_k=k if k else self.k)
        article_id_list, distance = zip(*search_results)
        articles = self.fetch_articles_by_article_id(article_id_list)
        result = to_result(articles)
      return result
    except Exception as e:
      raise e
