# Dockerfile

FROM python:3.12-slim

# Set working directory
WORKDIR /app

# Copy project files
COPY ./app ./app
COPY ./models ./models
COPY ./requirements.txt .

# Install dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Expose the FastAPI port
EXPOSE 8000

# Run the app using uvicorn
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]

