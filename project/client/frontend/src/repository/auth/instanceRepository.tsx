/* eslint-disable */
import { useDispatch } from 'react-redux';
import { ReactNode } from 'react';
import axios, { AxiosError, AxiosResponse } from 'axios';
import {
  useAccessToken,
  useRefreshToken,
  useNickname,
  AUTH_URI,
} from '../../data_source/apiInfo';
import { setTokens } from '../../store/store';

const instance = axios.create({
  timeout: 300,
  headers: {
    'Content-Type': 'application/json',
    authorization: '',
  },
});

function AxiosInterceptor({ children }: { children: ReactNode }) {
  const nickname = useNickname();
  const accessToken = useAccessToken();
  const refreshToken = useRefreshToken();

  const dispatched = useDispatch();
  const handleResponse = (response: AxiosResponse) => {
    const newAccessToken = (response.headers as { authorization: string })
      .authorization;
    const newRefreshToken = (
      response.headers as { authorization_refresh: string }
    ).authorization_refresh;
    // console.log(newAccessToken);
    // console.log(newRefreshToken);
    dispatched(setTokens(newAccessToken, newRefreshToken, nickname as string));
  };

  instance.interceptors.request.use(
    config => {
      config.headers.authorization = `Bearer ${accessToken as string}`;
      return config;
    },
    error => {
      return Promise.reject(error);
    },
  );

  instance.interceptors.response.use(
    response => {
      return response;
    },
    async (error: AxiosError) => {
      if (error.response?.status === 404) {
        // console.log(404);
        window.location.href = '/error';
      }
      if (error.response?.status === 401 && error.config) {
        // console.log(error);
        const { method, url: endPoint } = error.config;
        const headers = {
          Authorization: `Bearer ${accessToken as string}`,
          Authorization_refresh: `Bearer ${refreshToken as string}`,
        };

        if (method === 'get' || method === 'delete') {
          axios[method](endPoint as string, { headers })
            .then(response => {
              // console.log(response);
              handleResponse(response);
              setTimeout(() => {
                window.location.reload();
              }, 100);
            })
            .catch(() => {
              // console.log(e);
            });
        } else if (method === 'post') {
          axios[method](endPoint as string, {}, { headers })
            .then(response => {
              // console.log(response);
              if (endPoint !== `${AUTH_URI}/auth/logout`) {
                handleResponse(response);
              }
              setTimeout(() => {
                window.location.reload();
              }, 100);
            })
            .catch(e => {
              // console.log(e);
            });
        }

        return error;
      }
      return Promise.reject(error);
    },
  );
  return <div>{children}</div>;
}

export { AxiosInterceptor };
export default instance;
