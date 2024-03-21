from django.shortcuts import render

from rest_framework import status
from rest_framework.decorators import api_view, schema
from rest_framework.response import Response
from drf_yasg import openapi
from drf_yasg.utils import swagger_auto_schema
from .serializers import image_serializer

from status_classification import image_classification

@swagger_auto_schema(method='post', request_body=image_serializer)  # 요청 스키마 설정
@api_view(['POST'])
def status_classification(request):
    if request.method == 'POST':
        serializer = image_serializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            image_list = serializer.data['image_list']
            print(image_list)

            st = image_classification.get_image_status_by_image_list(image_list)

            return Response(st, status=status.HTTP_201_CREATED)
        
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@swagger_auto_schema(method='post', request_body=image_serializer)  # 요청 스키마 설정
@api_view(['POST'])
def test_clf(request):
    if request.method == 'POST':
        serializer = image_serializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            image_list = serializer.data['image_list']

            # 0번째 이미지를 받아서 리턴
            image = image_classification.image_to_tensor(image_list[0])
            st = image_classification.clf_predict(image)

            return Response(st, status=status.HTTP_201_CREATED)
        
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@swagger_auto_schema(method='post', request_body=image_serializer)  # 요청 스키마 설정
@api_view(['POST'])
def test_bcs(request):
    if request.method == 'POST':
        serializer = image_serializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            image_list = serializer.data['image_list']

            # 0번째 이미지를 받아서 리턴
            st = image_classification.test_test(image_list)

            return Response(st, status=status.HTTP_201_CREATED)
        
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
    
@swagger_auto_schema(method='post', request_body=image_serializer)  # 요청 스키마 설정
@api_view(['POST'])
def test_avg(request):
    if request.method == 'POST':
        serializer = image_serializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            image_list = serializer.data['image_list']

            # 0번째 이미지를 받아서 리턴
            i_list = image_list
            st = image_classification.list_avg(i_list)

            return Response(st, status=status.HTTP_201_CREATED)
        
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)