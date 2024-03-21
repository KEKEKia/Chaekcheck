from django.db import models

class Book(models.Model):
    publish_year = models.IntegerField()
    origin_price = models.FloatField()
    all_data = models.JSONField(default=list)  # "all" 필드를 저장할 JSONField
    back_data = models.JSONField(default=list)  # "back" 필드를 저장할 JSONField
    cover_data = models.JSONField(default=list)  # "cover" 필드를 저장할 JSONField
    side_data = models.JSONField(default=list)  # "side" 필드를 저장할 JSONField

    def __str__(self):
        return f"Book {self.id}"
