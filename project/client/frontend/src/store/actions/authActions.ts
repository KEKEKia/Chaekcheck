export const LOGOUT = 'LOGOUT';

export interface LogoutAction {
  type: typeof LOGOUT;
}

export const logout = (): LogoutAction => ({
  type: LOGOUT,
});

export const SET_TOKENS = 'SET_TOKENS';

export interface SetTokensAction {
  type: typeof SET_TOKENS;
  payload: {
    accessToken: string;
    refreshToken: string;
    nickname: string;
  };
}

export const setTokens = (
  accessToken: string,
  refreshToken: string,
  nickname: string,
): SetTokensAction => ({
  type: SET_TOKENS,
  payload: {
    accessToken,
    refreshToken,
    nickname,
  },
});
