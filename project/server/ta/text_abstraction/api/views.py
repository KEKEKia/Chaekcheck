# 기본 라이브러리 추가
from urllib import request

from django.conf import settings
# REST_Framework 라이브러리 추가
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework.utils import json

# 구글 클라우드 비전 API
import os
os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = settings.STATIC_ROOT + "/client_secret_key.json"
from google.cloud import vision

# Create your views here.
@api_view(['GET'])
def test(self):
    return Response({"message" : "API 호출 성공"})

@api_view(['POST'])
def bookScan(input):
    if input.method == 'POST':
        data = json.loads(input.body)
        path = data['img_url']

        # request.urlopen()
        content = request.urlopen(path).read()

        client = vision.ImageAnnotatorClient()

        image = vision.Image(content=content)

        response = client.text_detection(image=image)
        texts = response.text_annotations
        textList = list(str(texts[0].description).split("\n"))

        if response.error.message:
            raise Exception(
                "{}\nFor more info on error messages, check: "
                "https://cloud.google.com/apis/design/errors".format(response.error.message)
            )
        return Response(textList)
