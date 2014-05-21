from random import getrandbits
from time import time

try:
    import urllib.parse as urllib_parse
    from urllib.parse import urlencode
    PY3 = True
except ImportError:
    import urllib2 as urllib_parse
    from urllib import urlencode
    PY3 = False

import hashlib
import hmac
import base64

with open('twitter/secrets.txt', 'r') as secrets:
    CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_TOKEN_SECRET = [l.strip() for l in secrets.readlines()]

# apparently contrary to the HTTP RFCs, spaces in arguments must be encoded as
# %20 rather than '+' when constructing an OAuth signature (and therefore
# also in the request itself.)
# So here is a specialized version which does exactly that.
# In Python2, since there is no safe option for urlencode, we force it by hand
# Taken from sixohsix/twitter (https://github.com/sixohsix/twitter/)
def urlencode_noplus(query):
    if not PY3:
        new_query = []
        TILDE = '____TILDE-PYTHON-TWITTER____'
        for k,v in query:
            if type(k) is unicode: k = k.encode('utf-8')
            k = str(k).replace("~", TILDE)
            if type(v) is unicode: v = v.encode('utf-8')
            v = str(v).replace("~", TILDE)
            new_query.append((k, v))
        query = new_query
        return urlencode(query).replace(TILDE, "~").replace("+", "%20")

    return urlencode(query, safe='~').replace("+", "%20")
    
class OAuth(object):
    """
    An OAuth authenticator.
    """
    def __init__(self, token, token_secret, consumer_key, consumer_secret):
        """
        Create the authenticator. If you are in the initial stages of
        the OAuth dance and don't yet have a token or token_secret,
        pass empty strings for these params.
        """
        self.token = token
        self.token_secret = token_secret
        self.consumer_key = consumer_key
        self.consumer_secret = consumer_secret

    def encode_params(self, base_url, method, params):
        params = params.copy()

        if self.token:
            params['oauth_token'] = self.token

        params['oauth_consumer_key'] = self.consumer_key
        params['oauth_signature_method'] = 'HMAC-SHA1'
        params['oauth_version'] = '1.0'
        params['oauth_timestamp'] = str(int(time()))
        params['oauth_nonce'] = str(getrandbits(64))

        enc_params = urlencode_noplus(sorted(params.items()))

        key = self.consumer_secret + "&" + urllib_parse.quote(self.token_secret, safe='~')

        message = '&'.join(
            urllib_parse.quote(i, safe='~') for i in [method.upper(), base_url, enc_params])

        signature = (base64.b64encode(hmac.new(
                    key.encode('ascii'), message.encode('ascii'), hashlib.sha1)
                                      .digest()))
        return enc_params + "&" + "oauth_signature=" + urllib_parse.quote(signature, safe='~')

    def generate_headers(self):
        return {}

def urlencode_noplus(query):
    if not PY3:
        new_query = []
        TILDE = '____TILDE-PYTHON-TWITTER____'
        for k,v in query:
            if type(k) is unicode: k = k.encode('utf-8')
            k = str(k).replace("~", TILDE)
            if type(v) is unicode: v = v.encode('utf-8')
            v = str(v).replace("~", TILDE)
            new_query.append((k, v))
        query = new_query
        return urlencode(query).replace(TILDE, "~").replace("+", "%20")

    return urlencode(query, safe='~').replace("+", "%20")

domain = 'api.twitter.com'
api_version = '1.1'

"""

        # If an id kwarg is present and there is no id to fill in in
        # the list of uriparts, assume the id goes at the end.
        id = kwargs.pop('id', None)
        if id:
            uri += "/%s" % (id)

        # If an _id kwarg is present, this is treated as id as a CGI
        # param.
        _id = kwargs.pop('_id', None)
        if _id:
            kwargs['id'] = _id

        # If an _timeout is specified in kwargs, use it
        _timeout = kwargs.pop('_timeout', None)

        secure_str = ''
        if self.secure:
            secure_str = 's'
        dot = ""
        if self.format:
            dot = "."
        uriBase = "http%s://%s/%s%s%s" % (
            secure_str, self.domain, uri, dot, self.format)

        # Catch media arguments to handle oauth query differently for multipart
        media = None
        for arg in ['media[]', 'banner', 'image']:
            if arg in kwargs:
                media = kwargs.pop(arg)
                mediafield = arg
                break

        headers = {'Accept-Encoding': 'gzip'} if self.gzip else dict()
        body = None
        arg_data = None
        if self.auth:
            headers.update(self.auth.generate_headers())
            # Use urlencoded oauth args with no params when sending media
            # via multipart and send it directly via uri even for post
            arg_data = self.auth.encode_params(
                uriBase, method, {} if media else kwargs)
            if method == 'GET' or media:
                uriBase += '?' + arg_data
            else:
                body = arg_data.encode('utf8')

        # Handle query as multipart when sending media
        if media:
            BOUNDARY = "###Python-Twitter###"
            bod = []
            bod.append('--' + BOUNDARY)
            bod.append(
                'Content-Disposition: form-data; name="%s"' % mediafield)
            bod.append('')
            bod.append(media)
            for k, v in kwargs.items():
                bod.append('--' + BOUNDARY)
                bod.append('Content-Disposition: form-data; name="%s"' % k)
                bod.append('')
                bod.append(v)
            bod.append('--' + BOUNDARY + '--')
            body = '\r\n'.join(bod)
            headers['Content-Type'] = \
                'multipart/form-data; boundary=%s' % BOUNDARY

        req = urllib_request.Request(uriBase, body, headers)
        return self._handle_response(req, uri, arg_data, _timeout)"""