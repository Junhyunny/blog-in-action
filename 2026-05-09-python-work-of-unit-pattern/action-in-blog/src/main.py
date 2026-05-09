from contextlib import asynccontextmanager

from fastapi import Depends, FastAPI, Request
from fastapi.responses import JSONResponse

from core.database import Base, engine
from dependencies import get_todo_service
from todo.schema import TodoCreate, TodoResponse, TodoUpdate
from todo.service import TodoService


@asynccontextmanager
async def lifespan(app: FastAPI):
  async with engine.begin() as conn:
    await conn.run_sync(Base.metadata.create_all)
  yield


app = FastAPI(title="Todo API", lifespan=lifespan)


@app.exception_handler(ValueError)
async def not_found_handler(request: Request, exc: ValueError):
  return JSONResponse(status_code=404, content={"detail": str(exc)})


@app.get("/todos", response_model=list[TodoResponse])
async def get_todos(todo_service: TodoService = Depends(get_todo_service)):
  return await todo_service.get_all()


@app.get("/todos/{todo_id}", response_model=TodoResponse)
async def get_todo(todo_id: int, todo_service: TodoService = Depends(get_todo_service)):
  return await todo_service.get_by_id(todo_id)


@app.post("/todos", response_model=int, status_code=201)
async def create_todo(
  payload: TodoCreate, todo_service: TodoService = Depends(get_todo_service)
):
  return await todo_service.create(payload)


@app.patch("/todos/{todo_id}", response_model=None)
async def update_todo(
  todo_id: int,
  payload: TodoUpdate,
  todo_service: TodoService = Depends(get_todo_service),
):
  await todo_service.update(todo_id, payload)


@app.delete("/todos/{todo_id}", status_code=204)
async def delete_todo(
  todo_id: int, todo_service: TodoService = Depends(get_todo_service)
):
  await todo_service.delete(todo_id)
