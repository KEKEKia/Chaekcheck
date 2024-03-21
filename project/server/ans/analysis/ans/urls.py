from django.contrib import admin
from django.urls import path, include
from django.urls import path, re_path
from rest_framework.permissions import AllowAny
from drf_yasg.views import get_schema_view 
from drf_yasg import openapi
from rest_framework import routers
from api import views

from api.views import *

schema_view_v1 = get_schema_view(
    openapi.Info(
        title="Analysis API",
        default_version='v1',
        description="데이터 분석 API",
        terms_of_service="",
    ),
    public=True,
    permission_classes=(AllowAny,), 
)

urlpatterns = [
    path('analysis/bookprice', predict_book_price, name='predict_book_price'),

    # swagger
    re_path(r'^swagger(?P<format>\.json|\.yaml)$', schema_view_v1.without_ui(cache_timeout=0), name='schema-json'),
    re_path(r'^swagger/$', schema_view_v1.with_ui('swagger', cache_timeout=0), name='schema-swagger-ui'),
    re_path(r'^redoc/$', schema_view_v1.with_ui('redoc', cache_timeout=0), name='schema-redoc'),
]