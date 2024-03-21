FROM python:3.11

RUN pip install django
RUN pip install djangorestframework
RUN pip install --upgrade google-cloud-vision

WORKDIR /usr/src/app

COPY . .

WORKDIR /usr/src/app/text_abstraction

CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]

EXPOSE 8000
