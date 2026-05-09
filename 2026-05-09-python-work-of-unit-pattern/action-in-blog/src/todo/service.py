from core.unit_of_work import AbstractUnitOfWork
from todo.schema import TodoCreate, TodoResponse, TodoUpdate


class TodoService:
  def __init__(self, uow: AbstractUnitOfWork):
    self.uow = uow

  async def create(self, dto: TodoCreate) -> int:
    async with self.uow as uow:
      todo_id = await uow.todos.create(dto)
      await uow.commit()
    return todo_id

  async def get_all(self) -> list[TodoResponse]:
    async with self.uow as uow:
      todos = await uow.todos.get_all()
      return [TodoResponse.model_validate(todo) for todo in todos]

  async def get_by_id(self, todo_id: int) -> TodoResponse:
    async with self.uow as uow:
      todo = await uow.todos.get_by_id(todo_id)
      if todo is None:
        raise ValueError("Todo not found")
      return TodoResponse.model_validate(todo)

  async def update(self, todo_id: int, dto: TodoUpdate) -> None:
    async with self.uow as uow:
      await uow.todos.update(todo_id, dto)
      await uow.commit()

  async def delete(self, todo_id: int) -> None:
    async with self.uow as uow:
      await uow.todos.delete(todo_id)
      await uow.commit()
