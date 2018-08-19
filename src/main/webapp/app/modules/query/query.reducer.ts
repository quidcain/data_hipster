import axios from 'axios';

import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

export const ACTION_TYPES = {
  RUN_QUERY: 'account/UPDATE_PASSWORD'
};

const initialState = {
  loading: false,
  errorMessage: null,
  updateSuccess: false,
  updateFailure: false
};

export type QueryState = Readonly<typeof initialState>;

// Reducer
export default (state: QueryState = initialState, action): QueryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.RUN_QUERY):
      return {
        ...initialState,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case FAILURE(ACTION_TYPES.RUN_QUERY):
      return {
        ...initialState,
        loading: false,
        updateSuccess: false,
        updateFailure: true
      };
    case SUCCESS(ACTION_TYPES.RUN_QUERY):
      return {
        ...initialState,
        loading: false,
        updateSuccess: true,
        updateFailure: false
      };
    default:
      return state;
  }
};

// Actions
const apiUrl = 'api/account';

export const runQuery = (query, dataSourceId) => ({
  type: ACTION_TYPES.RUN_QUERY,
  payload: axios.post(`${apiUrl}/query`, { query, dataSourceId }),
  meta: {
    successMessage: '<strong>Query executed</strong>',
    errorMessage: '<strong>Error Running Query</strong> no data will be returned.'
  }
});
