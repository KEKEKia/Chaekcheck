// Tokens
import { useAccessToken } from '../../data_source/apiInfo';

// API
import {
  TaPredictDataSource,
  PredictApi,
} from '../../data_source/business/predictApi';

// interfaces
import { Book } from '../../interface/predictResult';

async function TaConfirmRepository(props: { imageList: File[] }) {
  const accessToken = useAccessToken();
  const { imageList } = props;
  if (accessToken) {
    const res = await TaPredictDataSource(accessToken, imageList);
    // console.log(res);
    if (res?.data) {
      return res.data.bookInfo;
    }
    return null;
  }
  // console.log('Error : repository token is not existed');
}

async function PredictRepository(props: { bookInfo: Book }) {
  const accessToken = useAccessToken();
  const { bookInfo } = props;
  if (accessToken) {
    const response = await PredictApi(accessToken, bookInfo);
    if (response?.data) {
      // console.log(response);
      if (response.code === 200) {
        return response.data;
      }
    }
    return null;
  }
  // console.log('Error : repository token is not existed');
}

export { PredictRepository, TaConfirmRepository };
