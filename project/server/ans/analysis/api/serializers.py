from rest_framework import serializers
from .models import Book  # 모델을 import 해야 합니다.

class BookSerializer(serializers.ModelSerializer):
    class Meta:
        model = Book
        fields = '__all__'  # 모든 필드를 시리얼라이즈하도록 지정합니다.
