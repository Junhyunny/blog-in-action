from fastapi import Depends

from core.database import DEFAULT_ASYNC_SESSION_FACTORY
from todo.service import TodoService
from todo.unit_of_work import SQLAlchemyUnitOfWork


def get_unit_of_work() -> SQLAlchemyUnitOfWork:
  return SQLAlchemyUnitOfWork(DEFAULT_ASYNC_SESSION_FACTORY)


def get_todo_service(
  unit_of_work: SQLAlchemyUnitOfWork = Depends(get_unit_of_work),
) -> TodoService:
  return TodoService(unit_of_work)
