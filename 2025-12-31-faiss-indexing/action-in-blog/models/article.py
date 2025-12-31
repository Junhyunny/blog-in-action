from dataclasses import dataclass
from typing import Dict, Any


@dataclass
class Article:
  id: int
  content: str

  def __init__(self, data: Dict[str, Any]):
    self.id = data["id"]
    self.content = data["content"]
