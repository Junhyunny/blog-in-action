import random
from typing import List

from faker import Faker

fake = Faker("en_US")


class Article:
  def __init__(self, id: int, title: str, content: str, url: str):
    self.id = id
    self.title = title
    self.content = content
    self.url = url


def fake_articles(num_records=1000) -> List[Article]:
  data: List[Article] = []
  for i in range(1, num_records + 1):
    doc_id = i
    title = fake.sentence(nb_words=random.randint(5, 12)).replace(".", "")

    content = ""
    while len(content) < 1000:
      paragraphs = fake.paragraphs(nb=random.randint(5, 10))
      new_text = "\n\n".join(paragraphs)
      content += new_text + "\n\n"

    doi_prefix = "10." + str(random.randint(1000, 9999))
    doi_suffix = fake.uuid4().split("-")[0]
    url = f"https://doi.org/{doi_prefix}/{doi_suffix}"

    data.append(Article(doc_id, title.title(), content.strip(), url))
  return data
