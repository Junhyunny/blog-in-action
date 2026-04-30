from database import DatabaseSession


class ReplyRepository:
  def __init__(self, session: DatabaseSession):
    self.session = session

  def get_all(self):
    return ["reply1", "reply2", "reply3"]
