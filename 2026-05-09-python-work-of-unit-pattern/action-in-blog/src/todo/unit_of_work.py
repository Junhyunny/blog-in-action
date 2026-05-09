from typing import Callable

from sqlalchemy.ext.asyncio import AsyncSession

from core.unit_of_work import AbstractUnitOfWork
from todo.repository import SQLAlchemyTodoRepository


class SQLAlchemyUnitOfWork(AbstractUnitOfWork):
  def __init__(self, async_session_factory: Callable[[], AsyncSession]):
    self.async_session_factory = async_session_factory
    self.session: AsyncSession | None = None

  async def __aenter__(self):
    self.session = self.async_session_factory()
    self.todos = SQLAlchemyTodoRepository(self.session)
    return await super().__aenter__()

  async def __aexit__(self, exc_type, exc_val, exc_tb):
    await super().__aexit__(exc_type, exc_val, exc_tb)
    await self.session.close()

  async def commit(self):
    await self.session.commit()

  async def rollback(self):
    await self.session.rollback()
