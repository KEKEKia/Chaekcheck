// Utils
import axios from 'axios';

import { BUSINESS_URI } from '../apiInfo';
// interface
import { Book } from '../../interface/predictResult';
import { TaResponse, PredictResponse } from '../../interface/api';

export async function TaPredictDataSource(token: string, imageList: File[]) {
  const url = `${BUSINESS_URI}/imageinfo`;
  const formData = new FormData();
  imageList.forEach(image => {
    formData.append('imageList', image);
  });
  let response: TaResponse | undefined | null;
  if (token) {
    await axios
      .post(url, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          Authorization: `Bearer ${token}`,
        },
      })
      .then(res => {
        // console.log(res.data);

        response = res.data as TaResponse;
      })
      // .catch(e => console.log(e));
      .catch(() => {});
  }
  return response;
}

export async function PredictApi(token: string, bookInfo: Book) {
  const url = `${BUSINESS_URI}/bookpredict`;
  let response: PredictResponse | undefined | null;
  if (token) {
    // console.log({ bookInfo });
    await axios
      .post(
        url,
        { bookInfo },
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        },
      )
      .then(res => {
        // console.log(res);
        response = res.data as PredictResponse;
      })
      // .catch(e => console.log(e));
      .catch(() => {});
  }
  return response;
}
