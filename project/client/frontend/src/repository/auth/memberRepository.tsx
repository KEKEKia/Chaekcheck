import { useAccessToken } from '../../data_source/apiInfo';
import SignoutAPI from '../../data_source/auth/member';
import { AuthRequset } from '../../interface/api';

function Signoutrepository({ dispatch }: AuthRequset): void {
  const accessToken = useAccessToken();
  if (accessToken) {
    SignoutAPI(accessToken, { dispatch });
  }
}

export default Signoutrepository;
