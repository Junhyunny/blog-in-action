.PHONY: fix run

fix:
	.venv/bin/ruff format .
	.venv/bin/ruff check --fix .

run:
	PYTHONPATH=src .venv/bin/uvicorn main:app --reload
