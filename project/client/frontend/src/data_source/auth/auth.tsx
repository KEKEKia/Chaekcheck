import { AUTH_URI } from '../apiInfo';
import { AuthRequset } from '../../interface/api';
import { logout } from '../../store/actions/authActions';
import instance from '../../repository/auth/instanceRepository';

function LogoutAPI(token: string, { dispatch }: AuthRequset): void {
  const logoutURI = `${AUTH_URI}/auth/logout`;
  if (token) {
    instance
      .post(
        logoutURI,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      )
      .then(() => {
        dispatch(logout());
        setTimeout(() => {
          window.location.href = '/';
        }, 100);
      })
      .catch(() => {});
  }
}

export default LogoutAPI;
