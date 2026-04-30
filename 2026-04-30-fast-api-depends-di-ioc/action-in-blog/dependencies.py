from typing import AsyncGenerator

from fastapi import Depends

from database import DatabaseSession
from reply_repository import ReplyRepository
from todo_repository import TodoRepository
from todo_service import TodoService


async def get_session() -> AsyncGenerator[DatabaseSession, None]:
  async with DatabaseSession() as session:
    yield session


def get_todo_repository(
  session: DatabaseSession = Depends(get_session, use_cache=False),
):
  return TodoRepository(session=session)


def get_reply_repository(
  session: DatabaseSession = Depends(get_session, use_cache=False),
):
  return ReplyRepository(session=session)


def get_todo_service(
  todo_repository: TodoRepository = Depends(get_todo_repository),
  reply_repository: ReplyRepository = Depends(get_reply_repository),
):
  return TodoService(todo_repository=todo_repository, reply_repository=reply_repository)
