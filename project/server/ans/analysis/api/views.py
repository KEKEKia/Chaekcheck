from django.shortcuts import render

from rest_framework import status
from rest_framework import viewsets
from rest_framework.decorators import api_view
from rest_framework.response import Response
from drf_yasg.utils import swagger_auto_schema
from .serializers import *

from price_prediction import price_prediction

@swagger_auto_schema(method='post', request_body=BookSerializer)  # 요청 스키마 설정
@api_view(['POST'])
def predict_book_price(request):
    if request.method == 'POST':
        serializer = BookSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            image_status = serializer.data
            print(image_status)

            price = price_prediction.price_prediction(image_status)

            return Response(price, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
