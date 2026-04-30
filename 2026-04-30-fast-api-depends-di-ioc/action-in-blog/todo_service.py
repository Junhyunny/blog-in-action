from reply_repository import ReplyRepository
from todo_repository import TodoRepository


class TodoService:
  def __init__(
    self, todo_repository: TodoRepository, reply_repository: ReplyRepository
  ):
    self.todo_repository = todo_repository
    self.reply_repository = reply_repository

  def get_all(self):
    return self.todo_repository.get_all()
