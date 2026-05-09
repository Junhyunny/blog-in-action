from abc import ABC, abstractmethod


class AbstractTodoRepository(ABC):
  @abstractmethod
  async def create(self, dto) -> int:
    raise NotImplementedError

  @abstractmethod
  async def get_all(self) -> list:
    raise NotImplementedError

  @abstractmethod
  async def get_by_id(self, todo_id: int):
    raise NotImplementedError

  @abstractmethod
  async def update(self, todo_id: int, dto) -> None:
    raise NotImplementedError

  @abstractmethod
  async def delete(self, todo_id: int) -> None:
    raise NotImplementedError
