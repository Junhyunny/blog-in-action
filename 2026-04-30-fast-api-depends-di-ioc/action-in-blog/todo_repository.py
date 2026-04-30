from database import DatabaseSession


class TodoRepository:
  def __init__(self, session: DatabaseSession):
    self.session = session

  def get_all(self):
    return ["todo1", "todo2", "todo3"]
