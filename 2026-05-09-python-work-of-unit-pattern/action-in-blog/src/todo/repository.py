from sqlalchemy import select
from sqlalchemy.ext.asyncio import AsyncSession

from core.repository import AbstractTodoRepository
from todo.model import Todo
from todo.schema import TodoCreate, TodoUpdate


class SQLAlchemyTodoRepository(AbstractTodoRepository):
  def __init__(self, session: AsyncSession):
    self.session = session

  async def create(self, dto: TodoCreate) -> int:
    todo = Todo(title=dto.title, description=dto.description)
    self.session.add(todo)
    await self.session.flush()
    return todo.id

  async def get_all(self) -> list[Todo]:
    result = await self.session.execute(select(Todo))
    return result.scalars().all()

  async def get_by_id(self, todo_id: int) -> Todo | None:
    result = await self.session.execute(select(Todo).filter(Todo.id == todo_id))
    return result.scalars().first()

  async def update(self, todo_id: int, dto: TodoUpdate) -> None:
    result = await self.session.execute(select(Todo).filter(Todo.id == todo_id))
    todo = result.scalars().first()
    if not todo:
      raise ValueError("Todo not found")
    if dto.title is not None:
      todo.title = dto.title
    if dto.description is not None:
      todo.description = dto.description
    if dto.completed is not None:
      todo.completed = dto.completed

  async def delete(self, todo_id: int) -> None:
    result = await self.session.execute(select(Todo).filter(Todo.id == todo_id))
    todo = result.scalars().first()
    if not todo:
      raise ValueError("Todo not found")
    await self.session.delete(todo)
