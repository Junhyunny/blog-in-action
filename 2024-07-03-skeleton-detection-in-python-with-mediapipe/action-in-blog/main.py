import json

import cv2
import mediapipe as mp

mp_drawing = mp.solutions.drawing_utils
mp_pose = mp.solutions.pose
pose = mp_pose.Pose()
POSE_LANDMARKS = [
    "nose", "left_eye_inner", "left_eye", "left_eye_outer", "right_eye_inner", "right_eye", "right_eye_outer",
    "left_ear", "right_ear", "mouth_left", "mouth_right", "left_shoulder", "right_shoulder", "left_elbow",
    "right_elbow", "left_wrist", "right_wrist", "left_pinky", "right_pinky", "left_index", "right_index",
    "left_thumb", "right_thumb", "left_hip", "right_hip", "left_knee", "right_knee", "left_ankle",
    "right_ankle", "left_heel", "right_heel", "left_foot_index", "right_foot_index"
]


def output_video(input_video):
    frame_width = int(input_video.get(cv2.CAP_PROP_FRAME_WIDTH))
    frame_height = int(input_video.get(cv2.CAP_PROP_FRAME_HEIGHT))
    fps = int(input_video.get(cv2.CAP_PROP_FPS))
    return cv2.VideoWriter(
        'output/result.mp4',
        cv2.VideoWriter_fourcc(*'MP4V'),
        fps,
        (frame_width, frame_height)
    )


def extract_landmarks(pose_landmarks):
    landmarks = []
    for (idx, landmark) in enumerate(pose_landmarks.landmark):
        landmarks.append({
            'x': landmark.x,
            'y': landmark.y,
            'z': landmark.z,
            'visibility': landmark.visibility,
            'body_part': POSE_LANDMARKS[idx]
        })
    return landmarks


def write_json(output_json_path: str, result_json):
    with open(output_json_path, 'w') as json_file:
        json.dump(result_json, json_file, indent=4)


def process():
    frame_index = 0
    result_json = []

    capture = cv2.VideoCapture('input/sample.mp4')
    output = output_video(capture)

    while capture.isOpened():
        ret, frame = capture.read()
        if not ret:
            break

        results = pose.process(
            cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        )

        if results.pose_landmarks:
            pose_landmarks = results.pose_landmarks
            mp_drawing.draw_landmarks(
                frame,
                pose_landmarks,
                mp_pose.POSE_CONNECTIONS
            )
            result_json.append({
                'frame': frame_index,
                'landmarks': extract_landmarks(pose_landmarks)
            })

        frame_index += 1
        output.write(frame)

    write_json('output/result.json', result_json)
    capture.release()
    output.release()


process()
