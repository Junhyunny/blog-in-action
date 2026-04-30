from fastapi import Depends, FastAPI

from dependencies import get_todo_service
from todo_service import TodoService

app = FastAPI()


@app.get("/")
async def read_root(todo_service: TodoService = Depends(get_todo_service)) -> list[str]:
  return todo_service.get_all()
