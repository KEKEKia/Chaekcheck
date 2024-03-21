import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { setTokens } from '../store/store';

function SocialLogin() {
  const dispatch = useDispatch();

  useEffect(() => {
    const handleCallback = () => {
      const urlParams = new URLSearchParams(window.location.search);
      const nickname = urlParams.get('nickname');
      const accessToken = urlParams.get('token');
      const refreshToken = urlParams.get('refreshToken');

      if (accessToken && refreshToken && nickname) {
        dispatch(setTokens(accessToken, refreshToken, nickname));
        setTimeout(() => {
          window.location.href = '/';
        }, 100);
      }
    };

    if (window.location.search.includes('token=')) {
      handleCallback();
    }
  }, [dispatch]);
  return <div />;
}

export default SocialLogin;
