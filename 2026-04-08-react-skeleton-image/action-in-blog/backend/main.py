import random
from pathlib import Path

import uvicorn
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import FileResponse, JSONResponse

app = FastAPI()

app.add_middleware(
  CORSMiddleware,
  allow_origins=["http://localhost:5173"],
  allow_methods=["GET"],
)

DUMMY_IMAGE_PATH = Path(__file__).parent / "resources" / "dummy.png"


@app.get("/images")
def get_image():
  if random.random() >= 0.5:
    return JSONResponse(status_code=404, content={"detail": "Not Found"})
  return FileResponse(DUMMY_IMAGE_PATH, media_type="image/png")


if __name__ == "__main__":
  uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)
