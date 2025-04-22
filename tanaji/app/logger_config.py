import logging
from pythonjsonlogger import json

# Create the logger
logger = logging.getLogger("tanaji-logger")
logger.setLevel(logging.INFO)

# Prevent duplicate logs if root logger is used elsewhere
logger.propagate = False

# Create console handler
console_handler = logging.StreamHandler()

# JSON formatter for structured logging
formatter = json.JsonFormatter(
    fmt='%(asctime)s %(levelname)s %(name)s %(message)s',
    rename_fields={
        'asctime': 'timestamp',
        'levelname': 'level',
        'message': 'msg'
    }
)

# Apply formatter to handler
console_handler.setFormatter(formatter)

# Attach the handler (only if not already added)
if not logger.handlers:
    logger.addHandler(console_handler)

