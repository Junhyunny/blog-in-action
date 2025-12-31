import json
import os
from dataclasses import asdict
from typing import List

from faker import Faker

from types.article import Article

fake = Faker("en_US")


def fake_articles(num_records=5) -> List[Article]:
  data: List[Article] = []
  for i in range(1, num_records + 1):
    id = i
    content = " ".join(fake.paragraphs(nb=1))
    data.append(Article(id, content.strip()))
  return data


if __name__ == "__main__":
  articles = fake_articles()
  dict_list = [asdict(article) for article in articles]
  os.makedirs(os.path.dirname("db/"), exist_ok=True)
  json.dump(dict_list, fp=open("db/articles.json", "w"))
