from sqlalchemy.ext.asyncio import async_sessionmaker, create_async_engine
from sqlalchemy.orm import DeclarativeBase

DATABASE_URL = "sqlite+aiosqlite:///./todos.db"

engine = create_async_engine(DATABASE_URL)
DEFAULT_ASYNC_SESSION_FACTORY = async_sessionmaker(
  autocommit=False, autoflush=False, bind=engine
)


class Base(DeclarativeBase):
  pass
