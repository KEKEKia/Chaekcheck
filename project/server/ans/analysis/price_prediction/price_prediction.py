def price_prediction(image_status):
    best_status_num = 0.7
    high_status_num = 0.6
    medium_status_num = 0.5
    low_status_num = 0.4

    print(image_status['origin_price'])
    origin_pirce = image_status['origin_price']
    probs = image_status['all_data']

    if probs.index(max(probs)) == 2:
        return 0
    
    all_price = []
    all_price.append(origin_pirce * probs[0] * best_status_num)
    all_price.append(origin_pirce * probs[1] * high_status_num)
    all_price.append(origin_pirce * probs[2] * low_status_num)
    all_price.append(origin_pirce * probs[3] * medium_status_num)


    return sum(all_price)
