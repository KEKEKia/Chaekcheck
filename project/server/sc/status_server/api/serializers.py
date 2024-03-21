from django.core import serializers
from rest_framework import serializers
from .models import image_list

class image_serializer(serializers.ModelSerializer):
    class Meta:
        model = image_list
        fields = '__all__'