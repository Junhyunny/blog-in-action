from fastapi import FastAPI, UploadFile
from fastapi.responses import HTMLResponse
from starlette.responses import FileResponse
import cv2

app = FastAPI()


def main_page():
    html_content = """
    <html>
        <head>
            <title>blog in action</title>
        </head>
        <body>
            <h3>Upload File</h1>
            <div>
                <video controls width="300">
                    <source src="/videos/result.webm" />
                </video>
            </div>
            <br/>
            <div>
                <input type="file" onchange="selectFile(this)" accept="video/*" />
            </div>
        </body>
    </html>
    <script>
        function selectFile(event) {
            const file = event.files[0]
            const formData = new FormData()
            formData.append("video", file)
            fetch("/videos", {
                method: "POST",
                body: formData
            }).then(() => {
                location.reload()
            })
        }
    </script>
    """
    return HTMLResponse(content=html_content, status_code=200)


@app.get("/", response_class=HTMLResponse)
async def index():
    return main_page()


@app.get("/videos/{name}")
async def video(name):
    return FileResponse("output/" + name, media_type='application/octet-stream', filename=name)


def processing(file_name: str):
    cap = cv2.VideoCapture("input/" + file_name)  # 1
    frame_width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
    frame_height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
    fps = int(cap.get(cv2.CAP_PROP_FPS))

    fourcc = cv2.VideoWriter_fourcc(*'VP80')  # 2

    out = cv2.VideoWriter("output/result.webm", fourcc, fps, (frame_width, frame_height))  # 3
    while cap.isOpened():
        ret, frame = cap.read()
        if not ret:
            break
        out.write(frame)


@app.post("/videos")
async def upload(video: UploadFile):
    file_path = f"input/{video.filename}"
    with open(file_path, "wb") as f:
        f.write(video.file.read())
    processing(video.filename)
