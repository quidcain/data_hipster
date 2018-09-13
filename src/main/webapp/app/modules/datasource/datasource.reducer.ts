import axios from 'axios';

import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';
import { IDatasource } from 'app/shared/model/datasource.model';

export const ACTION_TYPES = {
  TEST_DATASOURCE: 'api/test-datasource',
  GET_DATASOURCE: 'api/datasource'
};

const EMPTY_DATASOURCES: IDatasource[] = [];

const initialState = {
  loading: false,
  errorMessage: null,
  updateSuccess: false,
  updateFailure: false,
  datasources: EMPTY_DATASOURCES
};

export type DatasourceState = Readonly<typeof initialState>;

// Reducer
export default (state: DatasourceState = initialState, action): DatasourceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.TEST_DATASOURCE):
    case REQUEST(ACTION_TYPES.GET_DATASOURCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case FAILURE(ACTION_TYPES.TEST_DATASOURCE):
      return {
        ...state,
        loading: false,
        updateSuccess: false,
        updateFailure: true
      };
    case FAILURE(ACTION_TYPES.GET_DATASOURCE):
      return {
        ...state,
        loading: false,
        datasources: EMPTY_DATASOURCES
      };
    case SUCCESS(ACTION_TYPES.TEST_DATASOURCE):
      return {
        ...state,
        loading: false,
        updateSuccess: true,
        updateFailure: false
      };
    case SUCCESS(ACTION_TYPES.GET_DATASOURCE):
      return {
        ...state,
        loading: false,
        datasources: action.payload.data
      };
    default:
      return state;
  }
};

export const testDatasource = (hostname, username, password, schema) => ({
  type: ACTION_TYPES.TEST_DATASOURCE,
  payload: axios.post('api/test-datasource', { hostname, username, password, schema }),
  meta: {
    successMessage: 'Data Source Connected Successfully',
    errorMessage: 'Data Source Failed To Connect :('
  }
});

export const getDatasource = () => ({
  type: ACTION_TYPES.GET_DATASOURCE,
  payload: axios.get(ACTION_TYPES.GET_DATASOURCE)
});
