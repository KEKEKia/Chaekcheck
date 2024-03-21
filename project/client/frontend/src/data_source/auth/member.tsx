import { AUTH_URI } from '../apiInfo';
import { AuthRequset } from '../../interface/api';
import instance from '../../repository/auth/instanceRepository';
import { logout } from '../../store/actions/authActions';

function SignoutAPI(token: string, { dispatch }: AuthRequset): void {
  const SignoutURI = `${AUTH_URI}/member`;
  if (token) {
    instance
      .delete(SignoutURI, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then(() => {
        dispatch(logout());
        setTimeout(() => {
          window.location.href = '/';
        }, 100);
      })
      .catch(() => {});
  }
}

export default SignoutAPI;
