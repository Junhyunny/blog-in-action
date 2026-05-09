from abc import ABC, abstractmethod

from core.repository import AbstractTodoRepository


class AbstractUnitOfWork(ABC):
  todos: AbstractTodoRepository

  async def __aenter__(self):
    return self

  async def __aexit__(self, _exc_type, _exc_val, _exc_tb):
    await self.rollback()

  @abstractmethod
  async def commit(self):
    raise NotImplementedError

  @abstractmethod
  async def rollback(self):
    raise NotImplementedError
