import { useAccessToken } from '../../data_source/apiInfo';
import LogoutAPI from '../../data_source/auth/auth';
import { AuthRequset } from '../../interface/api';

function Logoutrepository({ dispatch }: AuthRequset): void {
  const accessToken = useAccessToken();
  if (accessToken) {
    LogoutAPI(accessToken, { dispatch });
  }
}

export default Logoutrepository;
