from abc import ABC, abstractmethod


class Repository(ABC):
  @abstractmethod
  def save(self, data: str):
    pass


class FileSystemRepository(Repository):
  def save(self, data: str):
    print(f"saving data in file system: {data}")


class DatabaseRepository(Repository):
  def save(self, data: str):
    print(f"saving data in database: {data}")


class Service:
  def __init__(self, repository: Repository):
    self.repository = repository

  def save_data(self, data: str):
    self.repository.save(data)
