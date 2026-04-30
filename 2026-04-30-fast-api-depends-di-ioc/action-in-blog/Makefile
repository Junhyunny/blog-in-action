VENV = .venv
PYTHON = $(VENV)/bin/python
RUFF = $(VENV)/bin/ruff

.PHONY: format lint fix run

format:
	$(RUFF) format .

lint:
	$(RUFF) check .

fix:
	$(RUFF) check --fix .
	$(RUFF) format .

run:
	$(PYTHON) -m uvicorn main:app --reload --host 0.0.0.0 --port 8000
