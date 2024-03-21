class_names = ['back','cover','side']

model_load = torch.load(f'../models/model_label-3_clf_600_resnet50.pth', map_location=device)
pred_result = []
rs = []
for img_type in class_names:
    for i in range(100,120):
        url = f'../data/book_data_3/clf/test/{img_type}/c{img_type[0]}_best ({i}).jpg'
        val, label = prediction(url,device=device,model_load=model_load)

        pred_result.append((img_type,label,val))
    rs.append((f'9_26_model_label-3_clf_600_resnet18',pred_result))
for i in rs:
    n,ps = i
    print(n)
    print(confusion_matrix(ps,class_names))