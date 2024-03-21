import { HistoriesResponse, DetailResponse } from '../../interface/api';
import { BUSINESS_URI } from '../apiInfo';
import instance from '../../repository/auth/instanceRepository';

const historyUri = `${BUSINESS_URI}/history`;
function HistoryAllApi(token: string) {
  const url = `${historyUri}/all`;
  if (token) {
    instance
      .get<HistoriesResponse>(url, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      })
      .then(response => {
        if (response.status === 200) {
          return response.data.data.history;
        }
        return response.statusText;
      })
      .catch(() => {});
  }
  return null;
}

async function HistoryDetailApi(token: string, bookId: number) {
  const url = `${historyUri}/${bookId}`;
  if (token) {
    try {
      const response = await instance.get<DetailResponse>(url, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });
      if (response.status === 200) {
        return response.data.data.bookInfo;
      }
      return response.statusText;
    } catch {}
  }
}

async function HistorySearchApi(token: string, keyword: string) {
  const url = `${historyUri}/search?keyword=${encodeURIComponent(keyword)}`;
  if (token) {
    try {
      const response = await instance.get<HistoriesResponse>(url, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.status === 200) {
        return response.data.data.history.reverse();
      }
      return response.statusText;
    } catch {}
  }
}

function HistoryDeleteApi(token: string, bookid: number) {
  const url = `${historyUri}/${bookid}`;
  if (token) {
    instance
      .delete(url, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      })
      .then(() => {})

      .catch(() => {});
  }
}

export { HistoryAllApi, HistoryDetailApi, HistorySearchApi, HistoryDeleteApi };
